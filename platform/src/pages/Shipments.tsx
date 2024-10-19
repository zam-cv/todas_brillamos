import { createColumns } from "@/components/table/components/columns-shipments";
import { DataTable } from "@/components/table/components/data-table-shipments";
import api, {Order}from "@/utils/api/orders";
import {useState, useEffect} from "react";

/**
 * Página de envíos
 * @author Sebastian Antonio Almanza 
 */
export default function Shipments() {
  //Constantes para almacenar los datos de las ordenes
  const [orders, setOrders] = useState<Order[]>([]); 

  //Obtener las ordenes de la base de
  useEffect(() => {
    api.orders.getOrders().then((response) => {
      setOrders(response.orders);
    });
  }, []);

  //Función para actualizar el estado de la orden
  function updateOrderStatus(orderID: number, status: string){
      api.orders.updateOrderStatus(orderID, {
        Status: status
      }).then(() => {
        api.orders.getOrders().then((response) => {
          setOrders(response.orders);
      });
    });
  }
  // Crear las columnas de la tabla
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