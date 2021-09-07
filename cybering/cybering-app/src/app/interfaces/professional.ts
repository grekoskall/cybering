export class RegisterInfo {
	email!: string;
	firstName!: string;
	lastName!: string;
	password!: string;
}

export class Professional {
	id!: String;
	email!: String;
	firstName!: String;
	lastName!: String;
	age!: String;
	phone!: String;
	photo!: String;
	workPosition!: String;
	workPlace!: String;
	bio!: String;
	workExperience!: String[];
	education!: String[];
	skills!: String[];
	password!: String;
}

export class PersonalInfoList {
	firstName !: String;
	lastName !: String;
	phone !: String;
	photo_url !: String;
	bio !: String;
	workPosition !: String;
	workPlace !: String;
	skills !: String[];
}