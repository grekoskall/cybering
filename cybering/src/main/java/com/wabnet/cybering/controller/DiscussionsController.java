package com.wabnet.cybering.controller;

import com.wabnet.cybering.model.advertisements.AdvertisementApplication;
import com.wabnet.cybering.model.bases.SimpleString;
import com.wabnet.cybering.model.discussions.Discussion;
import com.wabnet.cybering.model.discussions.DiscussionReply;
import com.wabnet.cybering.model.discussions.Message;
import com.wabnet.cybering.model.signin.tokens.Authentication;
import com.wabnet.cybering.model.users.Professional;
import com.wabnet.cybering.repository.discussions.DiscussionsRepository;
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

    public DiscussionsController(AuthenticationRepository authenticationRepository, ProfessionalRepository professionalRepository, DiscussionsRepository discussionsRepository) {
        this.authenticationRepository = authenticationRepository;
        this.professionalRepository = professionalRepository;
        this.discussionsRepository = discussionsRepository;
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
        Optional<Professional> professional = this.professionalRepository.findByEmail(token.getEmail());
        if (professional.isEmpty()) {
            System.out.println("\tThe email in authRep doesn't belong to a professional yet: " + token.getEmail());
            return null;
        }

        List<Discussion> discussionList = new LinkedList<>();
        discussionList.addAll(this.discussionsRepository.findAllByParticipant1(professional.get().getEmail()));
        discussionList.addAll(this.discussionsRepository.findAllByParticipant2(professional.get().getEmail()));
        if (discussionList.isEmpty())
            return null;
        discussionList.sort(new DiscussionComparator());
        for (Discussion currentDiscussion : discussionList) {
            String email1 = currentDiscussion.getParticipant1();
            String email2 = currentDiscussion.getParticipant2();
            Optional<Professional> professional1 = this.professionalRepository.findByEmail(email1);
            Optional<Professional> professional2 = this.professionalRepository.findByEmail(email2);
            if (professional1.isEmpty() || professional2.isEmpty()) {
                return null;
            }
            if (professional.get().getEmail().equals(email1)) {
                currentDiscussion.setParticipant1(professional1.get().getFirstName() + ' ' + professional1.get().getLastName());
                currentDiscussion.setParticipant2(professional2.get().getFirstName() + ' ' + professional2.get().getLastName());
            } else {
                currentDiscussion.setParticipant1(professional2.get().getFirstName() + ' ' + professional2.get().getLastName());
                currentDiscussion.setParticipant2(professional1.get().getFirstName() + ' ' + professional1.get().getLastName());
            }

            for (Message currentMessage : currentDiscussion.getMessagesArray()) {
                String email = currentMessage.getSender();
                Optional<Professional> messageSender = this.professionalRepository.findByEmail(email);
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
        Optional<Professional> professional = this.professionalRepository.findByEmail(token.getEmail());
        if (professional.isEmpty()) {
            System.out.println("\tThe email in authRep doesn't belong to a professional yet: " + token.getEmail());
            return new SimpleString("failed");
        }

        if (discussionReply.getMessage().isEmpty() || discussionReply.getMessage().replaceAll("[ \n\t]", "").equals("")) {
            return new SimpleString("failed");
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
        LocalDateTime localDateTime = LocalDateTime.now();

        Optional<Discussion> discussion = this.discussionsRepository.findById(discussionReply.getId());
        if (discussion.isEmpty())
            return new SimpleString("failed");
        Message[] messageArray = discussion.get().getMessagesArray();
        LinkedList<Message> messageList = new LinkedList<>(Arrays.asList(messageArray));
        messageList.addLast(new Message(dateTimeFormatter.format(localDateTime), professional.get().getEmail(), discussionReply.getMessage()));
        discussion.get().setMessagesArray(messageList.toArray(new Message[0]));
        this.discussionsRepository.save(discussion.get());

        System.out.println("\tReply set successfully");

        return new SimpleString("success");
    }

}
