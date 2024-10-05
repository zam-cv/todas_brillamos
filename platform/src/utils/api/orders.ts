import {get} from "@/utils/methods";

export interface Orders {
    quantity: number;
    delivery_date: string;
    status: string;
    ordered_receive_date: string;
    product_id: number;
    client_id: number;  
}


export default {
    orders: {
        getOrders: (): Promise<Orders[]> => {
            return get("/orders/all");
        }
    }
}