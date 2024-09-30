import {get, post, del, upload} from "../src/utils/methods";

export interface Admin {
    email: string; 
    password: string;
}

export default {
    admin: {
        loginAdmin: (
            admin: Admin
        ): Promise<void> => {
            return post("admin/auth/login", admin)
        },

        verifyAdmin: (
            admin: Admin
        ): Promise<void> => {
            return get(`admin/auth/${admin}/verify`)
        }
        
    }
}