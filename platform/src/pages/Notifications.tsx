import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Label } from "@/components/ui/label";
import { DataTable } from "@/components/table/components/tableNotifications/data-table-notifications";
import { columns as notificationColumns } from "@/components/table/components/tableNotifications/columns-notifications";  

import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";

import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";

export default function Notifications() {
  return (
    <div>
      <h2 className="scroll-m-20 border-b pb-2 text-3xl font-semibold tracking-tight first:mt-0">
        Notificaciones
      </h2>
      <br></br>
      <div className="overflow-auto gap-20 bg-#943370">
        <div>
          <Accordion type="single" collapsible className="w-3/4">
            <AccordionItem value="item-4">
              <AccordionTrigger className="font-semibold">
                Agregar Notificación
              </AccordionTrigger>
              <AccordionContent>
                <div className="flex flex-nowrap flex-col">
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Input
                      name="titulo"
                      value=""
                      placeholder="Título de la notificación"
                    ></Input>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Textarea
                      name="descripcion"
                      value=""
                      placeholder="Descripción..."
                    ></Textarea> 
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Button>Agregar</Button>
                  </div>
                </div>
              </AccordionContent>
            </AccordionItem>
          </Accordion>
        </div>
      </div>
      <div>
        <DataTable data={[]} columns={notificationColumns} event_id={1}/>
      </div>
    </div>
  );
}
