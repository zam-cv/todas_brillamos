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

export interface OrderInfo {
    id: number;
    first_name: string;
    last_name: string;
    email: string;
    price: number;
    quantity: number;
    product_id: number;
    client_id: number; 
    total_price: number;
 
}

export interface OrderInfoResponse {
    ordersInformation: OrderInfo[];
}



export default {
    orders: {
        // Cambiamos el tipo de retorno a 'Promise<OrdersResponse>' en vez de 'Promise<Order[]>'
        getOrders: (): Promise<OrdersResponse> => {
            return get("/orders/all");
        },

        getOrdersInfo: (): Promise<OrderInfo[]> => {
            return get("/orders/info");
        }
    }
}
