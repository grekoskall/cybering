# Cybering
Business Networking Application

This is an implementation of a new business networking application, called Cybering. The application will be accessible via the web.

This application offers 2 roles: Administrator / Professional. Each role has its own unique user interface.

  Administrator: This role is assigned to predefined user(s) at the installation process of the application.
  Administrators can manage the users of the application and export their data.

  Professional: Professionals can manage their Info, make Connect requests to other Professionals and Accept or Refuse Connect requests,
  oversee and manage their Connection Network, post Articles that can have Images/Video, post and reply to Ads, see/reply at their Timeline to
  Articles that their Connections have posted, Note their interest to specific posts and get Notified of the interest of other users at their posts,
  make private discussions with their Connections, explore the Profiles of other Professionals and modify their connection settings.

  Info - Education, Work Experience.

This application has the following requirements:

* The application starts by offering the user a Welcome Page. This page provides log-in and sign-in capabilities to the user. The user will be
  asked for his/her e-mail address. These requests are encrypted by SSL/TLS.
* The sign-in page of a new user requests a password, password verification, name, surname, e-mail address, telephone number, photo and some
  other info. If another user with the same e-mail address already exists, a suitable message will be shown and the process will stop.
* The application has a pre-installed administrator user. When the administrator successfully logs-in he/she will be redirected to the Manage Page.
* From the Manage Page the administrator can explore the users list. From that list he/she can explore each users information page. The administrator
  can also export the data of one or more users in XML/JSON fashion. This data includes the following: their bio, posts of articles/ads, work experience,
  interest notes and replies to other posts, connections network.
* When a user logs-in he/she gets redirected to the Home Page. The menu there is: Home Page, Network, Ads, Discussions, Notifications, Personal Info, Settings.
  These are shown as active or inactive tabs. The left side of the page has a field in which a user can explore his/her personal information as well as
  his/her network. At the central part of the page his/her timeline is shown, where the articles that he/she and other connections have posted, in chronological
  order, in which he/she can reply or note interest. Other articles of non-connections are also shown, but for which users that are in his/her network have note
  their interest. Finally, there is a field of text/media(photo, video, audio-files) from which a new article can be posted.
* The Network Page shows in a web diagram the Connections(fullname, photo, work position, workplace). By selecting one of the Connections, redirect to their
  information page. From that page, the user can initiate a private discussion. There is also a search field for other registered users. The results are shown as
  a list from which the user can explore their information page. In the case of a non-connection the user can't see their network and can only see the information
  that the non-connection has declared public.
* The Ads Page shows the advertisements that other connected users have posted and can select to apply to one of them. The user can also create their own ads and
  check the applications of other users to them. The page also shows advertisements of non-connections. The appearance is based on the skills that the user has
  defined in their personal information as well as based on the Matrix Factorization Collaborative Filtering algorithm that uses the data of the network. The
  vectors that are created correspond to the users in the ads space. The coordinates values are calculated by projecting the ads to the users.
* The Discussion Page at its left side shows the list of the private discussions that the user has initiated with other users. In the central part the messages
  in the last or the selected discussion from the left side list is shown.
* The Notifications Page has 2 parts: the upper part shows connection requests that the user can accept or refuse, as well as links to other users from which the
  user can see their personal information. The bottom part shows other users interest notes and their replies to users articles.
* From the Personal Info Page the user can update their work experience, education and skills. The user can also define which of this information is private and
  which is public.
* From the Settings Page the user can change their e-mail address and their password.
* At the users Timeline, the articles shown are based on a recommendation algorithm that processes the past likes of the user to articles of other users, the replies
  to those articles and the user network. For the generation of recommendationts the Matrix Factorization algorithm is used. In case a user notes interest or doesn't
  reply, the application will note and utilize the projection of the articles to create the vectors that represent the users in the articles space. The ordering is
  by users that belong to the same network as the user.
