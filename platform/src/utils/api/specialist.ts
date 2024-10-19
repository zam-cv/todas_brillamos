import {get, post} from "../methods";

/**
 * API para la información de los especialistas
 * @Author Sebastian Antonio Almanza
 */

// Interfaz para los datos de los especialistas
export interface Specialist {
    id: number;
    FirstName: string;
    LastName: string;
    phone: string;
    specialty: string;
    description: string;

}


export default {
    specialist: {
        // Endpoint para crear un especialista
        setSpecialist: (
            specialist: Specialist
        ): Promise<Specialist> => {
            return post("/specialists", specialist)
        },
        // Endpoint para obtener los especialistas
        getSpacialist: (): Promise<Specialist[]> => {
            return get("/specialist")
        }
    }
}