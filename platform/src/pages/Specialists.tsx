import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Textarea } from "@/components/ui/textarea"
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion"

import { IoIosAddCircleOutline } from "react-icons/io";


export default function Specialists() {
  return (
    <div>
       <h2 className="scroll-m-20 border-b pb-2 text-3xl font-semibold tracking-tight first:mt-0">
          Especialistas
      </h2>   
      <br></br>
      <div className="overflow-auto gap-5 ">
          <div>
            <Button>
              <IoIosAddCircleOutline className="mr-2 h-4 w-4"/>Añadir Especialista
            </Button>
          </div>
          <div>
            <Button>
              <IoIosAddCircleOutline className="mr-2 h-4 w-4"/>Añadir Producto
            </Button>
          </div>
          <div>
            <Button>
              <IoIosAddCircleOutline className="mr-2 h-4 w-4"/>Añadir Etiqueta
            </Button>
          </div>
          <div>
            <Accordion type="single" collapsible className="w-3/4">
               <AccordionItem value="item-4">
                <AccordionTrigger className="font-semibold">Agregar Artículo</AccordionTrigger>
                  <AccordionContent>
                      <div className="flex flex-nowrap flex-col">
                        <div className="flex flex-row space-x-2 px-2 pt-2">
                          <Input name="nombreArt" value= "" placeholder="Nombre del artículo"></Input>
                        </div>
                        <div className="flex flex-row space-x-2 px-2 pt-2">
                           <Input name="author" value ="" placeholder="Autor"></Input>
                        </div>
                        <div className="flex flex-row space-x-2 px-2 pt-2">
                          <Textarea name="articulo" value="" placeholder="Artículo..."></Textarea>
                        </div>
                      </div>
                  </AccordionContent>
               </AccordionItem>
            </Accordion>
          </div>
       
      </div>
      
      
    </div>
  )
}