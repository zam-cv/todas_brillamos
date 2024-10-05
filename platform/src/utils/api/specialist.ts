import {get, post} from "../methods";

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
        setSpecialist: (
            specialist: Specialist
        ): Promise<Specialist> => {
            return post("/specialists", specialist)
        },

        getSpacialist: (): Promise<Specialist[]> => {
            return get("/specialist")
        }
    }
}