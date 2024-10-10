import { get } from "@/utils/methods";
import { Product } from "@/utils/api/products";

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

export interface MostSellProducts {
    id: number;
    name: string;
    order_count: number;
}

export interface MonthlyRevenue {
    month: string;
    total_revenue: number;
}


export default {
    orders: {
        // Cambiamos el tipo de retorno a 'Promise<OrdersResponse>' en vez de 'Promise<Order[]>'
        getOrders: (): Promise<OrdersResponse> => {
            return get("/orders/all");
        },

        getOrdersInfo: (): Promise<OrderInfo[]> => {
            return get("/orders/info");
        },

        getMostSell: (): Promise<MostSellProducts[]> => {
            return get("/orders/BestSell")
        },

        getMonthlyRevenue: (): Promise<MonthlyRevenue[]> => {
            return get("/orders/monthRev")
        }
    }
}
