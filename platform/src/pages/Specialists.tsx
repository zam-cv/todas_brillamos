import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Label } from "@/components/ui/label";
import { useState, useEffect } from "react";
import api, { Category} from "@/utils/api/category";
import apiPost, { Posts } from "@/utils/api/post";  
import { Badge } from "@/components/ui/badge"

import {
  Drawer,
  DrawerClose,
  DrawerContent,
  DrawerDescription,
  DrawerFooter,
  DrawerHeader,
  DrawerTitle,
  DrawerTrigger,
} from "@/components/ui/drawer"



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
import { set } from "zod";


export default function Specialists() {
  const [name, setName] = useState<string>("");
  const [category, setCategory] = useState<Category[]>([]);
  
  const[title, setTitle] = useState<string>("");
  const [author, setAuthor] = useState<string>("");  
  const [content, setContent] = useState<string>("");
  const [date, setDate] = useState<string>("");

  const[isCategoryValid, setIsCategoryValid] = useState(false);
  useEffect(() => {
    const categoryValid = name !== "";
    setIsCategoryValid(categoryValid);
  }, [name]);

  const[isPostValid, setIsPostValid] = useState(false); 
  useEffect(() => {
    const postValid = title !== "" && author !== "" && content !== "";
    setIsPostValid(postValid);
  }, [title, author, content]);
  

  function uploadCategory() {
    api.category.setCategory({
      name,
    } as any).then(() => {
      console.log("Category added")
      setName("");
    })
  }

  function uploadPost() {
    apiPost.posts.setPost({
      title,
      author,
      date: "2021-10-10",
      content
    } as any).then(() => {
      console.log("Post added")
      setTitle("");
      setAuthor("");
      setContent("");
    })
  }

  

  useEffect(() => {
    api.category.getCategories().then((category) => {
      setCategory(category);
    });
  }, [])



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
                      value={name}
                      onChange = {(e) => setName(e.target.value)}
                      className="col-span-2 h-8"
                      placeholder="Nombre de la categoría"
                    />
                  </div>
                  <div className="grid grid-cols-3 items-center gap-4">
                    <Button onClick={uploadCategory} disabled={!isCategoryValid}>Añadir</Button>
                  </div>
                </div>
              </div>
            </PopoverContent>
          </Popover>
        </div>
        {
          category.map((category) => (
            <Drawer key={category.id}>
              <Badge key={category.id} variant="outline" className="cursor-pointer">{category.name}</Badge>
            </Drawer>
          ))
        }
        <br></br>
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
        <br></br>
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
                      value={title}
                      placeholder="Nombre del artículo"
                      onChange={(e) => setTitle(e.target.value)}
                    ></Input>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Input 
                      name="author" 
                      value={author} 
                      onChange={(e) => setAuthor(e.target.value)}
                      placeholder="Autor"></Input>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Textarea
                      name="articulo"
                      value={content}
                      onChange={(e) => setContent(e.target.value)}
                      placeholder="Artículo..."
                    ></Textarea>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Button onClick={uploadPost} disabled={!isPostValid}>Agregar</Button>
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
