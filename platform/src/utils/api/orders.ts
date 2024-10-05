import { get } from "@/utils/methods";

// Ajustamos las propiedades de acuerdo al formato del JSON proporcionado
export interface Order {
    Quantity: number;
    DeliveryDate: string;
    Status: string;
    ProductID: number;
    ClientID: number;
}

// La estructura de la respuesta contiene un array de 'Order'
export interface OrdersResponse {
    orders: Order[];
}

export default {
    orders: {
        // Cambiamos el tipo de retorno a 'Promise<OrdersResponse>' en vez de 'Promise<Order[]>'
        getOrders: (): Promise<OrdersResponse> => {
            return get("/orders/all");
        }
    }
}
