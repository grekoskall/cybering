export class Discussion {
	
	id !: String;
	participant1 !: String;
	participant2 !: String;
	messagesArray !: Message[];
}

export class DiscussionsInfo {
	discussionArray !: Discussion[];
	selectedIndex !: number;
}

export class Message {

	id !: String;
	timeStamp !: String;
	sender !: String;
	message !: String;
}

export class DiscussionReply {
	id !: String;
	message !: String;
}