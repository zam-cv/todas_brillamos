import { createColumns } from "@/components/table/components/columns-shipments";
import { DataTable } from "@/components/table/components/data-table-shipments";
import api, {Order}from "@/utils/api/orders";
import {useState, useEffect} from "react";

export default function Shipments() {
  const [orders, setOrders] = useState<Order[]>([]); // Cambia a un array de 'Order'

  useEffect(() => {
    api.orders.getOrders().then((response) => {
      setOrders(response.orders); // Accede al array de órdenes dentro del objeto 'OrdersResponse'
    });
  }, []);


  function updateOrderStatus(orderID: number, status: string){
      api.orders.updateOrderStatus(orderID, {
        Status: status
      }).then(() => {
        api.orders.getOrders().then((response) => {
          setOrders(response.orders);
      });
    });
  }

  const columns = createColumns(updateOrderStatus);
  

    return (
      <div>
      <h2 className="scroll-m-20 text-3xl font-semibold tracking-tight first:mt-0">
            Envíos
      </h2>
      <br></br>      
        <DataTable 
          data = {orders}
          columns={columns}
          event_id={1}
        
        />
        
      </div>
    );
}