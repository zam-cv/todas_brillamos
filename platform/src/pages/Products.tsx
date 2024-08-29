import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { columns } from "@/components/table/components/columns-products";
import { DataTable } from "@/components/table/components/data-table-products";

export default function Products() {
  const mockData = [
    { idProducto: 'XDcaw1', nombreProd: 'Producto #1', cantidad: '12' }
  ];


  return(
    <div>
      <h2 className="scroll-m-20 border-b pb-2 text-3xl font-semibold tracking-tight first:mt-0">
          Productos
      </h2>
      <div className="flex w-full max-w-sm items-center space-x-2">
        <Input type= "search" placeholder = "search" />
        <Button type= "submit">Añadir Producto</Button>
      </div>

    <br></br>

    <DataTable
      data = {mockData}
      columns = {columns}
      event_id = {1}
    />
      
      
    </div>
  ) 
        
  
  
}