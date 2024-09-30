import {get, del, post, upload } from "../methods";

export interface User{
    email: string;
    password: string;
    first_name: string;
    last_name: string;
}

export default {
    user: {
        verifyUser: (
            user: User
        ): Promise<void> => {
            return get(`admin/auth/${user}/verify`)
        },

        registerUser: (
            user: User
        ): Promise<void> => {
            return post("admin/auth/register", user)
        },

        loginUser: (
            user: User
        ): Promise<void> => {
            return post("admin/auth/login", user)
        }

    }
}