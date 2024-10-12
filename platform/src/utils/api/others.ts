import {get, del, post, upload } from "../methods";

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
        setOthers: (
            others: Others,
            file: File
        ): Promise<void> => {
            return upload("admin/others", file, others, false)
        },

        getOthers: (): Promise<void> => {
            return get("admin/others")
        },

    
    },

    
}