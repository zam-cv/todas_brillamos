import {get,  post,  } from "../methods";

/**
 * API para la autenticación de los usuarios
 * @Author Sebastian Antonio Almanza
 */

// Interfaz para los datos de los usuarios
export interface User{
    email: string;
    password: string;
    first_name: string;
    last_name: string;
}

export default {
    user: {
        // Endpoint para verificar un usuario
        verifyUser: (
            user: User
        ): Promise<void> => {
            return get(`admin/auth/${user}/verify`)
        },
        // Endpoint para registrar un usuario
        registerUser: (
            user: User
        ): Promise<void> => {
            return post("admin/auth/register", user)
        },
        // Endpoint para iniciar sesión como usuario
        loginUser: (
            user: User
        ): Promise<void> => {
            return post("admin/auth/login", user)
        }

    }
}