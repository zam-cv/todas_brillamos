import {get, post} from "../methods";

/**
 * API para la información de las notificaciones
 * @Author Sebastian Antonio Almanza
 */

// Interfaz para los datos de las notificaciones
export interface Notification {
    id: string;
    title: string;
    description: string;
    date: string;
    client_id: number;  
}

export default {
    notification: {
        // Endpoint para obtener las notificaciones
        setNotification: (
            notification: Notification,
        ): Promise<Notification> => {
            return post(`/notifications`, notification);
        },

        // Endpoint para obtener una notificación
        getNotifications: (): Promise<Notification[]> =>{
            return get("/notifications");
        }



    }
}