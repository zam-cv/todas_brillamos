import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { DataTable } from "@/components/table/components/tableNotifications/data-table-notifications";
import { columns as notificationColumns } from "@/components/table/components/tableNotifications/columns-notifications";  
import api, {Notification}from "@/utils/api/notifications"
import { useEffect } from "react";

import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";
import { useState } from "react";

export default function Notifications() {
  const [notification, setNotification] = useState<Notification[]>([]);
  const [title, setTitle] = useState<string>(""); 
  const [description, setDescription] = useState<string>(""); 
  const [date] = useState<string>("");
  const [client_id] = useState<number>(0);

  function uploadNotification() {
    api.notification.setNotification({
      title,
      description,
      date : "2021-10-10",
      client_id,
    } as any).then((data) => {
        const id = data.id;
        setNotification([
          ...notification,
          {
            id,
            title,
            description,
            date,
            client_id,
          }
        ]);
    });
  }


  useEffect(() => {
    api.notification.getNotifications().then((notification) => {
      setNotification(notification);
    })
  }, []);


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
                      value={title}
                      placeholder="Título de la notificación"
                      onChange={(e) => setTitle(e.target.value)}
                    ></Input>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Textarea
                      name="descripcion"
                      value={description}
                      placeholder="Descripción..."
                      onChange={(e) => setDescription(e.target.value)}
                    ></Textarea> 
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Button onClick={uploadNotification}>Agregar</Button>
                  </div>
                </div>
              </AccordionContent>
            </AccordionItem>
          </Accordion>
        </div>
      </div>
      
        <DataTable data={notification} columns={notificationColumns} event_id={1}/>

    </div>
  );
}
