import { columns } from "@/components/table/components/columns-shipments";
import { DataTable } from "@/components/table/components/data-table-shipments";

export default function Shipments() {
  const mockData = [
    {idProducto: "dadAW", nombreProd: "Producto #1", cantidad: "1", numGuia: "123", estadoEnvio: "En progreso"}
  ];

  return (
    <div>
    <h2 className="scroll-m-20 text-3xl font-semibold tracking-tight first:mt-0">
          Envíos
    </h2>
    <br></br>      
      <DataTable 
        data = {mockData}
        columns={columns}
        event_id={1}
      
      />
      
    </div>
  )
}