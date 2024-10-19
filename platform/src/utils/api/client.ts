import {get} from "../methods";

/**
 * API para la información de los clientes
 * @Author Sebastian Antonio Almanza
 */

// Interfax para los datos de los clientes
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
        // Endpoint para obtener la información de los clientes
        getClientsInfo: (id: number): Promise<ClientInfo[]> => {
            return get(`/clients/getInfo/${id}`);
        },

        // Endpoint para obtener los ids de los clientes
        getClientsIDs: (): Promise<number[]> => {
            return get("/clients/getIDS");
        }
    }
}