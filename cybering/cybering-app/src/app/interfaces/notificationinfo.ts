export class NotificationInfo {
	senderProfid !: string;
	fullName !: string;
	type !: NotificationType;
	timestamp !: string;
	info !: string;
}

export enum NotificationType {
	LIKE,
    COMMENT,
    APPLY_AD,
    CHAT_MESSAGE,
    CONNECTION_REQUEST,
    CONNECTION_APPROVED
}