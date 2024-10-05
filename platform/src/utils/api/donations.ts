import {get} from "@/utils/methods";

export interface Donation {
    amount: number;
    description: string;
    date: string;

}

export default {
    donations: {
        getDonations: (): Promise<Donation[]> => {
            return get("/donations");
        }
    }
}