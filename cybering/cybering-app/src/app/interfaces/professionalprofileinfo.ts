export class ProfessionalProfileInfo {
    profid !: string;
    email !: string;
    firstName !: string;
    lastName !: string;
    age !: string;
    phone !: string;
    photo !: string;
    workPosition !: string;
    workPlace !: string;
    bio !: string;
    workExperience !: string[];
    education !: string[];
    skills !: string[];
	userStatus !: UserStatus;


}

export enum UserStatus {
    CONNECTED,
    NOT_CONNECTED,
    SENT_REQUEST,
    RECEIVED_REQUEST,
	IS_ADMIN,
	SAME_USER
}