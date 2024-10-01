import {get, post, del, upload} from "@/utils/methods";

export interface Admin {
    email: string;
    password: string;
}

export default {
    admin: {
        loginAdmin: (
            email: string ,
            password: string
        ): Promise<void> => {
            return post("/auth/admin/signin", {email, password}, false);
        },

        verifyAdmin: (): Promise<Admin> => {
            return get("/auth/admin/verify");
        }
        
    }
}