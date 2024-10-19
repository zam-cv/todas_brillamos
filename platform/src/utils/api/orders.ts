import { get, post } from "@/utils/methods";

/**
 * API para la información de las ordenes
 * @Author Sebastian Antonio Almanza
 */

// Interfaz para los datos de las ordenes
export interface Order {
    Quantity: number;
    DeliveryDate: string;
    Status: string;
    ProductID: number;
    ClientID: number;
}

// Interfaz para los datos de las ordenes
export interface OrdersResponse {
    orders: Order[];
}

// Interfaz para los datos de las ordenes
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
// Interfaz para los datos de las ordenes
export interface OrderInfoResponse {
    ordersInformation: OrderInfo[];
}

// Interfaz para los datos de los productos mas vendidos
export interface MostSellProducts {
    id: number;
    name: string;
    order_count: number;
}

// Interfaz para obtener la venta mensual
export interface MonthlyRevenue {
    month: string;
    total_revenue: number;
}

// Interfaz para cambiar el estado de la orden
export interface SetStatus {
    Status: string;
}


export default {
    orders: {
        // Endpoint para obtener las ordenes
        getOrders: (): Promise<OrdersResponse> => {
            return get("/orders/all");
        },

        // Endpoint para obtener la información de las ordenes
        getOrdersInfo: (): Promise<OrderInfo[]> => {
            return get("/orders/info");
        },


        // Endpoint para obtener los productos mas vendidos
        getMostSell: (): Promise<MostSellProducts[]> => {
            return get("/orders/BestSell")
        },

        // Endpoint para obtener la venta mensual
        getMonthlyRevenue: (): Promise<MonthlyRevenue[]> => {
            return get("/orders/monthRev")
        },

        // Endpoint para cambiar el estado de la orden
        updateOrderStatus: (
            orderID: number, 
            status: SetStatus ): Promise<void> => {
            return post(`/orders/${orderID}`, status)
        }
    }
}
