import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Label } from "@/components/ui/label";
import { useState, useEffect } from "react";
import api, { Category } from "@/utils/api/category";
import apiPost, { Posts } from "@/utils/api/post";  
import { Badge } from "@/components/ui/badge";
import { DataTable } from "@/components/table/components/tablePosts/data-table-posts";
import { createColumns } from "@/components/table/components/tablePosts/columns-posts";
import apiSpecialist, { Specialist} from "@/utils/api/specialist";

import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"


import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover";
import { Accordion, AccordionContent, AccordionItem, AccordionTrigger } from "@/components/ui/accordion";

/**
 * Página de añadir 
 * @author Sebastian Antonio Almanza
 */

export default function Specialists() {
  //Constantes para almacenar los datos de la categoría, especialistas y artículos
  const [name, setName] = useState<string>("");
  const [category, setCategory] = useState<Category[]>([]);
  const [title, setTitle] = useState<string>("");
  const [author, setAuthor] = useState<string>("");
  const [content, setContent] = useState<string>("");
  const [post, setPosts] = useState<Posts[]>([]);
  const [idPost, setIdPost] = useState<number | null>(null);
  const [isCategoryValid, setIsCategoryValid] = useState(false);
  const [isPostValid, setIsPostValid] = useState(false); 
  const [FirstName, setFirstName] = useState<string>("");
  const [LastName, setLastName] = useState<string>("");
  const [phone, setPhone] = useState<string>("");
  const [specialty, setSpecialty] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [specialist, setSpecialist] = useState<Specialist[]>([]); 

  // Validar campos de la categoría
  useEffect(() => {
    const categoryValid = name !== "";
    setIsCategoryValid(categoryValid);
  }, [name]);

  // Validar campos de el artículo
  useEffect(() => {
    const postValid = title !== "" && author !== "" && content !== "";
    setIsPostValid(postValid);
  }, [title, author, content]);

  // Función para subir la categoría a la base de datos
  function uploadCategory() {
    api.category.setCategory({
      name,
    } as any).then((data) => {
      const id = data.id;
      setCategory([...category, { id, name } as any]);
      console.log("Category added");
      setName("");
    });
  }

  // Función para subir el artículo a la base de datos
  function uploadPost() {
    apiPost.posts.setPost({
      title,
      author,
      date: new Date().toISOString(),
      content,
    } as any).then((data) => {
      const id = data.id;
      setPosts([...post, { id, title, author, content, date: data.date } as any]);
      console.log("Post added");
      setTitle("");
      setAuthor("");
      setContent("");
    });
  }

  // Función para subir el especialista a la base de datos
  function uploadSpecialist() {
    apiSpecialist.specialist.setSpecialist({
      FirstName,
      LastName,
      phone,
      specialty,
      description
    } as any).then((data) => {
      const id = data.id;
      setSpecialist([
        ...specialist,
        {
          id,
          FirstName,
          LastName,
          phone,
          specialty,
          description
        }
      ]);
      setFirstName("");
      setLastName("");
      setPhone("");
      setSpecialty("");
      setDescription("");
    });
  }

  // Función para eliminar un post
  function handleDelete(postId: number) {
    apiPost.posts.deletePost(postId)
      .then(() => {
        setPosts(post.filter(post => post.id !== postId));
        console.log("Post deleted");
      })
      .catch(error => {
        console.error("Error deleting the post:", error);
      });
  }

  // Obtener categorías
  useEffect(() => {
    api.category.getCategories().then((category) => {
      setCategory(category);
    });
  }, []);

  // Obtener posts
  useEffect(() => {
    apiPost.posts.getPosts().then((post) => {
      setPosts(post);
    });
  }, []);

  // Obtener especislitas
  useEffect(() => {
    apiSpecialist.specialist.getSpacialist().then((specialist) => {
      setSpecialist(specialist);
    })
  })

  // Obtener el id del post
  useEffect(() => {
    console.log(idPost);
  }, [idPost]);


  // Función para actualizar la información
  function updateProduct(value: Object, fields: Posts) {
    apiPost.posts.updatePost(fields.id,{
      ...fields,
      ...value
    } as any);
  }


  // Crear las columnas de la tabla
  const columns = createColumns(handleDelete, updateProduct);

  return (
    <div>
      <h2 className="scroll-m-20 border-b pb-2 text-3xl font-semibold tracking-tight first:mt-0">
        Agregar...
      </h2>
      <br></br>
      <div className="overflow-auto gap-20 bg-#943370">
        {/* Categorías */}
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
                      onChange={(e) => setName(e.target.value)}
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
        <div className="flex flex-wrap gap-4"> {}
          {category.map((category) => (
              <Badge key={category.id} variant="outline" className="cursor-pointer">
                {category.name}
                </Badge>
          ))}
        </div> 
        </div>      
        <br></br>
        <Popover>
      <PopoverTrigger asChild>
        <Button>Añadir Especialista</Button>
      </PopoverTrigger>
      <PopoverContent className="w-80">
        <div className="grid gap-4">
          <div className="space-y-2">
            <h4 className="font-medium leading-none">Especialista</h4>
            <p className="text-sm text-muted-foreground">
              Complete los siguientes campos.
            </p>
          </div>
          <div className="grid gap-2">
            <div className="grid grid-cols-3 items-center gap-4">
              <Label htmlFor="width">Nombre</Label>
              <Input
                name="firstname"
                value={FirstName}
                className="col-span-2 h-8"
                onChange={(e) => setFirstName(e.target.value)}
              />
            </div>
            <div className="grid grid-cols-3 items-center gap-4">
              <Label htmlFor="lastname">Segundo Nombre</Label>
              <Input
                  name="lastname"
                  value={LastName}
                  className="col-span-2 h-8"
                  onChange={(e) => setLastName(e.target.value)}
                />
              </div>
            <div className="grid grid-cols-3 items-center gap-4">
              <Label htmlFor="maxWidth">Especialidad</Label>
              <Input
                name="specialty"
                value={specialty}
                onChange={(e)=> setSpecialty(e.target.value)}
                className="col-span-2 h-8"
              />
            </div>
            <div className="grid grid-cols-3 items-center gap-4">
              <Label htmlFor="height">Contacto</Label>
              <Input
                name="phone"
                value={phone}
                onChange={(e)=> setPhone(e.target.value)}
                className="col-span-2 h-8"
              />
            </div>
            <div className="grid grid-cols-3 items-center gap-4">
              <Label htmlFor="height">Descripcion</Label>
              <Input
                name="description"
                value={description}
                onChange={(e)=> setDescription(e.target.value)}
                className="col-span-2 h-8"
              />
            </div>
          </div>
          <Button onClick={uploadSpecialist}>Agregar</Button>
        </div>
      </PopoverContent>
    </Popover>
        <br></br>
        <br></br>
        {specialist.map((specialist)=>(
          <Card>
            <CardHeader>
                <CardTitle>{specialist.FirstName}</CardTitle>
                <CardTitle>{specialist.LastName}</CardTitle>
            </CardHeader>
            <CardContent>
              <CardDescription>{specialist.specialty}</CardDescription>
              <CardDescription>{specialist.phone}</CardDescription>
              <CardDescription>{specialist.description}</CardDescription>
            </CardContent>
          </Card>
        ))}
        <br></br>

        {/* Posts */}
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
                      placeholder="Autor"
                    ></Input>
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

        <br></br>
      </div>
      <DataTable data={post} columns={columns} setId={setIdPost}/>

    </div>
  );
}
