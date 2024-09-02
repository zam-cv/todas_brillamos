import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { columns } from "@/components/table/components/columns-products";
import { DataTable } from "@/components/table/components/data-table-products";
import { IoIosAdd, IoIosAddCircleOutline } from "react-icons/io";
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion"


export default function Products() {
  const mockData = [
    { idProducto: 'XDcaw1', nombreProd: 'Producto #1', cantidad: '12' }
  ];


  return(
    <div>
      <h2 className="scroll-m-20  text-3xl font-semibold tracking-tight first:mt-0">
          Productos
      </h2>
    <br></br>
    <Accordion type="single" collapsible>
        <AccordionItem value="item-1">
          <AccordionTrigger>Agregar</AccordionTrigger>
            <AccordionContent>
              <div className = "flex flex-row flex-nowrap w-3/5 space-x-2.5">
                  <Input placeholder="Nombre del producto"></Input>
                  <Input placeholder="Cantidad"></Input>
                  <Input placeholder="Precio"></Input>

              </div>
            </AccordionContent>
        </AccordionItem>
    </Accordion>
    


    <br></br>

    <DataTable
      data = {mockData}
      columns = {columns}
      event_id = {1}
    />
      
      
    </div>
  ) 
        
  
  
}