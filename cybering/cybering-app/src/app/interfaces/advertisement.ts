export class Advertisement {
	id !: string;
	firstName !: string;
	lastName !: string;
	startDate !: string;
	endDate !: string;
	workPlace !: string;
	workPosition !: string;
	title !: string;
	description !: string;
	skills !: string[];
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
