export class PersonalInfo {
    workPosition !: String;
    workPlace !: String;
    bio !: String;
    workExperience !: String[];
    education !: String[];
    skills !: String[];
    privacySettings !: PrivacySettings;
}

export class PrivacySettings {
	workExperience !: Privacy;
    education !: Privacy;
    skills !: Privacy;
}

export enum Privacy {
    PRIVATE,
    PUBLIC
}