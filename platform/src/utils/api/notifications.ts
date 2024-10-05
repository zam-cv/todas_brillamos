import {get, post} from "../methods";

export interface Notification {
    id: string;
    title: string;
    description: string;
    date: string;
    client_id: number;  
}

export default {
    notification: {
        setNotification: (
            notification: Notification,
        ): Promise<Notification> => {
            return post(`/notifications/${notification.client_id}`, notification);
        },

        getNotifications: (): Promise<Notification[]> =>{
            return get("/notifications");
        }



    }
}