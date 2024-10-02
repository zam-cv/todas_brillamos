import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Label } from "@/components/ui/label";

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


export default function Specialists() {
  return (
    <div>
      <h2 className="scroll-m-20 border-b pb-2 text-3xl font-semibold tracking-tight first:mt-0">
        Agregar...
      </h2>
      <br></br>
      <div className="overflow-auto gap-20 bg-#943370">
      <div>
        <Popover>
            <PopoverTrigger asChild>
              <Button>Agregar Categoría</Button>
            </PopoverTrigger>
            <PopoverContent className="w-80">
              <div className="grid gap-4">
                <div className="space-y-2">
                  <h4 className="font-medium leading-none">Añadir categoría</h4>
                  <p className="text-sm text-muted-foreground">
                    Complete los siguientes datos
                  </p>
                </div>
                <div className="grid gap-2">
                  <div className="grid grid-cols-3 items-center gap-4">
                    <Label htmlFor="nombre">Nombre</Label>
                    <Input
                      id="nombre"
                      defaultValue=""
                      className="col-span-2 h-8"
                    />
                  </div>
                  <div className="grid grid-cols-3 items-center gap-4">
                    <Button>Añadir</Button>
                  </div>
                </div>
              </div>
            </PopoverContent>
          </Popover>
        </div>
        <br></br>
        <div>
          <Popover>
            <PopoverTrigger asChild>
              <Button>Agregar Especialista</Button>
            </PopoverTrigger>
            <PopoverContent className="w-80">
              <div className="grid gap-4">
                <div className="space-y-2">
                  <h4 className="font-medium leading-none">Añadir especialista</h4>
                  <p className="text-sm text-muted-foreground">
                    Complete los siguientes datos
                  </p>
                </div>
                <div className="grid gap-2">
                  <div className="grid grid-cols-3 items-center gap-4">
                    <Label htmlFor="nombre">Nombre</Label>
                    <Input
                      id="nombre"
                      defaultValue=""
                      className="col-span-2 h-8"
                    />
                  </div>
                  <div className="grid grid-cols-3 items-center gap-4">
                    <Label htmlFor="especialidad">Especialidad</Label>
                    <Input
                      id="especialidad"
                      defaultValue=""
                      className="col-span-2 h-8"
                    />
                  </div>
                  <div className="grid grid-cols-3 items-center gap-4">
                    <Label htmlFor="contact">Contacto</Label>
                    <Input
                      id="contact"
                      defaultValue="+52 "
                      className="col-span-2 h-8"
                    />
                  </div>
                  <div className="grid grid-cols-3 items-center gap-4">
                    <Button>Añadir</Button>
                  </div>
                </div>
              </div>
            </PopoverContent>
          </Popover>
        </div>
        <div>
          <Accordion type="single" collapsible className="w-3/4">
            <AccordionItem value="item-4">
              <AccordionTrigger className="font-semibold">
                Agregar Artículo
              </AccordionTrigger>
              <AccordionContent>
                <div className="flex flex-nowrap flex-col">
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Input
                      name="nombreArt"
                      value=""
                      placeholder="Nombre del artículo"
                    ></Input>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Input name="author" value="" placeholder="Autor"></Input>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Textarea
                      name="articulo"
                      value=""
                      placeholder="Artículo..."
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
    </div>
  );
}
