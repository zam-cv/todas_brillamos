import {get, post} from "@/utils/methods";

/**
 * API para la autenticación de administradores
 * @author Sebastian Antonio Almanza
 */

// interfaz para las credenciales de autenticación
export interface Credentials {
    token: string;
}

// interfaz para los datos del administrador
export interface Admin {
    email: string;
    password: string;
}

export default {
    admin: {

        // Endpoint para iniciar sesión como administrador con solicitud POST
        loginAdmin: (
            email: string ,
            password: string
        ): Promise<Credentials> => {
            return post("/auth/admin/signin", {email, password}, false);
        },

        // Endpoint para verificar si el usuario es administrador con solicitud GET
        verifyAdmin: (): Promise<Admin> => {
            return get("/auth/admin/verify");
        }
        
    }
}