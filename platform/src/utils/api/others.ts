import {get, upload } from "../methods";

/**
 * API para la información adicional de los usuarios
 * @Author Sebastian Antonio Almanza
 */

// Interfaz para los datos de los usuarios
export interface Others{
    CURP: string;
    Street: string;
    Interior: string;
    Exterior: string;
    City: string;
    State: string;
    ZIP: string;
    Reference: string;
    ClientID: null

}


export default {
    others: {
        // Endpoint para registrar la información adicional de los usuarios
        setOthers: (
            others: Others,
            file: File
        ): Promise<void> => {
            return upload("admin/others", file, others, false)
        },

        // Endpoint para obtener la información adicional de los usuarios
        getOthers: (): Promise<void> => {
            return get("admin/others")
        },

    
    },

    
}