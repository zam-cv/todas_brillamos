import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { columns } from "@/components/table/components/columns-products";
import { DataTable } from "@/components/table/components/data-table-products";
import { IoIosAdd, IoIosAddCircleOutline } from "react-icons/io";
import { Textarea } from "@/components/ui/textarea"

import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion"


export default function Products() {
  const mockData = [
    { idProducto: 'XDcaw1', 
      nombreProd: 'Producto #1', 
      cantidad: '12' , 
      precio: '12.00', 
      marca: 'Marca #1', 
      tamano: '12x12', 
      material: 'Material #1', 
      absorbencia: '12', 
      cuidadoPiel: 'Cuidado #1', 
      color: 'Color #1', 
      descripcion: 'Descripcion #1' },
      { idProducto: 'XDcaw1', 
        nombreProd: 'Producto #1', 
        cantidad: '12' , 
        precio: '12.00', 
        marca: 'Marca #1', 
        tamano: '12x12', 
        material: 'Material #1', 
        absorbencia: '12', 
        cuidadoPiel: 'Cuidado #1', 
        color: 'Color #1', 
        descripcion: 'Descripcion #1' },
        { idProducto: 'XDcaw1', 
          nombreProd: 'Producto #1', 
          cantidad: '12' , 
          precio: '12.00', 
          marca: 'Marca #1', 
          tamano: '12x12', 
          material: 'Material #1', 
          absorbencia: '12', 
          cuidadoPiel: 'Cuidado #1', 
          color: 'Color #1', 
          descripcion: 'Descripcion #1' },
          { idProducto: 'XDcaw1', 
            nombreProd: 'Producto #1', 
            cantidad: '12' , 
            precio: '12.00', 
            marca: 'Marca #1', 
            tamano: '12x12', 
            material: 'Material #1', 
            absorbencia: '12', 
            cuidadoPiel: 'Cuidado #1', 
            color: 'Color #1', 
            descripcion: 'Descripcion #1' },
  ];


  return(
    <div>
      <h2 className="scroll-m-20  text-3xl font-semibold tracking-tight first:mt-0">
          Productos
      </h2>
    <br></br>
    <Accordion type="single" collapsible className="w-3/4">
        <AccordionItem value="item-1">
          <AccordionTrigger className="font-semibold">Agregar</AccordionTrigger>
            <AccordionContent>
              <div className = "flex flex-nowrap flex-col">
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Input className="w-4/5" placeholder="Nombre del producto"></Input>
                    <Input className="w-3/4 " placeholder="Cantidad"></Input>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Input placeholder="Precio"></Input>
                    <Input placeholder="Marca"></Input>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Input placeholder="Tamaño"></Input>
                    <Input placeholder="Precio"></Input>
                    <Input placeholder="Material"></Input>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Input placeholder="Absorbencia"></Input>
                    <Input placeholder="Cuidado de la piel"></Input>
                    <Input placeholder="Color"></Input>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                      <Textarea placeholder="Descripción.."/>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                      <Button>Agregar</Button>
                  </div>
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