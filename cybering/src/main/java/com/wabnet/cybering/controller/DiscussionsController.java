package com.wabnet.cybering.controller;

import com.wabnet.cybering.model.bases.SimpleString;
import com.wabnet.cybering.model.discussions.Discussion;
import com.wabnet.cybering.model.discussions.DiscussionReply;
import com.wabnet.cybering.model.discussions.DiscussionsInfo;
import com.wabnet.cybering.model.discussions.Message;
import com.wabnet.cybering.model.notifications.NotificationInfo;
import com.wabnet.cybering.model.notifications.NotificationType;
import com.wabnet.cybering.model.notifications.Notifications;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.discussions.DiscussionsRepository;
import com.wabnet.cybering.repository.notifications.NotificationsRepository;
import com.wabnet.cybering.repository.users.ProfessionalRepository;
import com.wabnet.cybering.repository.validation.AuthenticationRepository;
import com.wabnet.cybering.utilities.DiscussionComparator;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class DiscussionsController {
    private final AuthenticationRepository authenticationRepository;
    private final ProfessionalRepository professionalRepository;
    private final DiscussionsRepository discussionsRepository;
    private final NotificationsRepository notificationsRepository;

    public DiscussionsController(AuthenticationRepository authenticationRepository, ProfessionalRepository professionalRepository, DiscussionsRepository discussionsRepository, NotificationsRepository notificationsRepository) {
        this.authenticationRepository = authenticationRepository;
        this.professionalRepository = professionalRepository;
        this.discussionsRepository = discussionsRepository;
        this.notificationsRepository = notificationsRepository;
    }

    @PostMapping(value="/cybering/discussions", headers = "action=discussion-array")
    public Discussion[] discussionArray(@RequestHeader HttpHeaders httpHeaders) {
        System.out.println("\tGot a request to discussion array.");
        String cookie = httpHeaders.getFirst("Cookies");
        if (cookie == null) {
            System.out.println("\tCookie header is empty");
            return null;
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if (token == null) {
            System.out.println("\tThe cookie doesn't match the records");
            return null;
        }
        Optional<Professional> professional = this.professionalRepository.findById(token.getProfid());
        if (professional.isEmpty()) {
            System.out.println("\tThe Id in authRep doesn't belong to a professional yet: " + token.getProfid());
            return null;
        }

        List<Discussion> discussionList = new LinkedList<>();
        discussionList.addAll(this.discussionsRepository.findAllByParticipant1(professional.get().getId()));
        discussionList.addAll(this.discussionsRepository.findAllByParticipant2(professional.get().getId()));
        if (discussionList.isEmpty())
            return null;
        discussionList.sort(new DiscussionComparator());
        for (Discussion currentDiscussion : discussionList) {
            String profid1 = currentDiscussion.getParticipant1();
            String profid2 = currentDiscussion.getParticipant2();
            Optional<Professional> professional1 = this.professionalRepository.findById(profid1);
            Optional<Professional> professional2 = this.professionalRepository.findById(profid2);
            if (professional1.isEmpty() || professional2.isEmpty()) {
                return null;
            }
            if (professional.get().getId().equals(profid1)) {
                currentDiscussion.setParticipant1(professional1.get().getFirstName() + ' ' + professional1.get().getLastName());
                currentDiscussion.setParticipant2(professional2.get().getFirstName() + ' ' + professional2.get().getLastName());
            } else {
                currentDiscussion.setParticipant1(professional2.get().getFirstName() + ' ' + professional2.get().getLastName());
                currentDiscussion.setParticipant2(professional1.get().getFirstName() + ' ' + professional1.get().getLastName());
            }

            for (Message currentMessage : currentDiscussion.getMessagesArray()) {
                String profid = currentMessage.getSender();
                Optional<Professional> messageSender = this.professionalRepository.findById(profid);
                if (messageSender.isEmpty())
                    return null;

                currentMessage.setSender(messageSender.get().getFirstName() + ' ' + messageSender.get().getLastName());
            }

        }

        System.out.println("\tReturning a Discussion Array.");
        return discussionList.toArray(new Discussion[0]);
    }

    @PostMapping(value="/cybering/discussions", headers = "action=discussion-reply")
    public SimpleString discussionReply(@RequestHeader HttpHeaders httpHeaders, @RequestBody DiscussionReply discussionReply) {
        System.out.println("\tGot a request to discussion array.");
        String cookie = httpHeaders.getFirst("Cookies");
        if (cookie == null) {
            System.out.println("\tCookie header is empty");
            return new SimpleString("failed");
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if (token == null) {
            System.out.println("\tThe cookie doesn't match the records");
            return new SimpleString("failed");
        }
        Optional<Professional> professional = this.professionalRepository.findById(token.getProfid());
        if (professional.isEmpty()) {
            System.out.println("\tThe Id in authRep doesn't belong to a professional yet: " + token.getProfid());
            return new SimpleString("failed");
        }

        if (discussionReply.getMessage().isEmpty() || discussionReply.getMessage().replaceAll("[ \n\t]", "").equals("")) {
            return new SimpleString("failed");
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime localDateTime = LocalDateTime.now();

        Optional<Discussion> discussion = this.discussionsRepository.findById(discussionReply.getId());
        if (discussion.isEmpty())
            return new SimpleString("failed");
        Message[] messageArray = discussion.get().getMessagesArray();
        LinkedList<Message> messageList = new LinkedList<>(Arrays.asList(messageArray));
        messageList.addLast(new Message(dateTimeFormatter.format(localDateTime), professional.get().getId(), discussionReply.getMessage()));
        discussion.get().setMessagesArray(messageList.toArray(new Message[0]));
        this.discussionsRepository.save(discussion.get());

        System.out.println("\tReply set successfully");
        // Create appropriate Notification
        String profid1 = discussion.get().getParticipant1();
        String  profid2 = discussion.get().getParticipant2();
        Optional<Professional> receiverProfessional;
        if (profid1.equals(professional.get().getId()))
            receiverProfessional = professionalRepository.findById(profid2);
        else
            receiverProfessional = professionalRepository.findById(profid1);
        if (receiverProfessional.isPresent()) {
            Optional<Notifications> profilesNotifications = notificationsRepository.findById(receiverProfessional.get().getId());
            NotificationInfo newNotificationInfo = new NotificationInfo(professional.get().getId(), NotificationType.CHAT_MESSAGE, " " + professional.get().getFirstName() + " " + professional.get().getLastName());
            profilesNotifications.get().getNotificationsList().addFirst(newNotificationInfo);
            notificationsRepository.save(profilesNotifications.get());
        }

        return new SimpleString("success");
    }

    @PostMapping(value="/cybering/discussions", headers = "action=discussion-array-with-param")
    public DiscussionsInfo discussionArrayWithParam(@RequestHeader HttpHeaders httpHeaders, @RequestBody SimpleString profidFromUrl) {
        System.out.println("\tGot a request to discussion array.");
        String cookie = httpHeaders.getFirst("Cookies");
        if (cookie == null) {
            System.out.println("\tCookie header is empty");
            return null;
        }
        Authentication token = this.authenticationRepository.findByToken(cookie);
        if (token == null) {
            System.out.println("\tThe cookie doesn't match the records");
            return null;
        }
        Optional<Professional> professional = this.professionalRepository.findById(token.getProfid());
        if (professional.isEmpty()) {
            System.out.println("\tThe Id in authRep doesn't belong to a professional yet: " + token.getProfid());
            return null;
        }

        int i = 0, index = -1;


        List<Discussion> discussionList = new LinkedList<>();
        discussionList.addAll(this.discussionsRepository.findAllByParticipant1(professional.get().getId()));
        discussionList.addAll(this.discussionsRepository.findAllByParticipant2(professional.get().getId()));
        if (discussionList.isEmpty())
            return null;
        discussionList.sort(new DiscussionComparator());

        Optional<Professional> professionalToMsg = this.professionalRepository.findById(profidFromUrl.getData());

        for (Discussion currentDiscussion : discussionList) {
            String profid1 = currentDiscussion.getParticipant1();
            String profid2 = currentDiscussion.getParticipant2();
            Optional<Professional> professional1 = this.professionalRepository.findById(profid1);
            Optional<Professional> professional2 = this.professionalRepository.findById(profid2);
            if (professional1.isEmpty() || professional2.isEmpty()) {
                return null;
            }

            if (professional.get().getId().equals(profid1)) {
                currentDiscussion.setParticipant1(professional1.get().getFirstName() + ' ' + professional1.get().getLastName());
                currentDiscussion.setParticipant2(professional2.get().getFirstName() + ' ' + professional2.get().getLastName());

                if (professionalToMsg.isPresent() && professionalToMsg.get().getId().equals(profid2)) {
                    index = i;
                }
            } else {
                currentDiscussion.setParticipant1(professional2.get().getFirstName() + ' ' + professional2.get().getLastName());
                currentDiscussion.setParticipant2(professional1.get().getFirstName() + ' ' + professional1.get().getLastName());

                if (professionalToMsg.isPresent() && professionalToMsg.get().getId().equals(profid1)) {
                    index = i;
                }
            }

            for (Message currentMessage : currentDiscussion.getMessagesArray()) {
                String profid = currentMessage.getSender();
                Optional<Professional> messageSender = this.professionalRepository.findById(profid);
                if (messageSender.isEmpty())
                    return null;

                currentMessage.setSender(messageSender.get().getFirstName() + ' ' + messageSender.get().getLastName());
            }

            i++;
        }

        if (index == -1) {
            if (professionalToMsg.isEmpty()) {
                index = 0;
            } else {
                Discussion newDiscussion = new Discussion(professional.get().getId(), professionalToMsg.get().getId());
                newDiscussion = this.discussionsRepository.save(newDiscussion);
                newDiscussion.setParticipant1(professional.get().getFirstName() + ' ' + professional.get().getLastName());
                newDiscussion.setParticipant2(professionalToMsg.get().getFirstName() + ' ' + professionalToMsg.get().getLastName());

                discussionList.add(newDiscussion);
                index = discussionList.size() - 1;
            }
        }

        System.out.println("\tReturning Discussions info");

        return new DiscussionsInfo(discussionList.toArray(new Discussion[0]), index);
    }

}
