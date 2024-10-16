import {get} from "@/utils/methods";

export interface Donation {
    product_id : number;
    product_name : string;
    amount : number;
    user_email : string;
    first_name : string;
    last_name : string;
    curp: string;

}

export default {
    donations: {
        getDonations: (): Promise<Donation[]> => {
            return get("/donations");
        }
    }
}