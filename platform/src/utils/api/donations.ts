import {get} from "@/utils/methods";

/**
 * API para la información de las donaciones
 * @Author Sebastian Antonio Almanza
 */

// Interfaz para los datos de las donaciones
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
        // Endpoint para obtener las donaciones
        getDonations: (): Promise<Donation[]> => {
            return get("/donations");
        }
    }
}