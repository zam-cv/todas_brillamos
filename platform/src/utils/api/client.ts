import {get} from "../methods";

export interface ClientInfo{
    client_id: number;
    first_name: string;
    last_name: string;
    email: string;
    curp: string;
    street: string;
    z_ip: string;
    reference: string;  
}

export default {
    client: {
        getClientsInfo: (id: number): Promise<ClientInfo[]> => {
            return get(`/clients/getInfo/${id}`);
        },

        getClientsIDs: (): Promise<number[]> => {
            return get("/clients/getIDS");
        }
    }
}