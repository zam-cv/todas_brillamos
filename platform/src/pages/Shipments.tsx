import { columns } from "@/components/table/components/columns-shipments";
import { DataTable } from "@/components/table/components/data-table-shipments";
import api, {Orders}from "@/utils/api/orders";
import {useState, useEffect} from "react";

export default function Shipments() {
  const [orders, setOrders] = useState<Orders[]>([]); 

  useEffect(() => {
    api.orders.getOrders().then((orders) =>{
      setOrders(orders);
    });
  }, []);

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
  )
}