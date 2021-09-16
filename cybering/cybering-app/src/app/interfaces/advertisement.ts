export class Advertisement {
	id !: String;
	firstName !: String;
	lastName !: String;
	startDate !: String;
	endDate !: String;
	workPlace !: String;
	workPosition !: String;
	title !: String;
	description !: String;
	skills !: String[];
}

export class AdvertisementPost {
	title !: string;
	description !: string;
	skills !: string;
	endDate !: string;
}

export class AdvertisementApplication {
	id !: string;
	title !: string;
	description !: string;
	skills !: string[];
	startDate !: string;
	endDate !: string;
	applicants !: string[][];
}
