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
import Delete, { DeleteWrapper } from '@/components/Delete';

import {
  Drawer,
  DrawerContent,
  DrawerFooter,
  DrawerHeader,
  DrawerTitle,
} from "@/components/ui/drawer";

import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover";
import { Accordion, AccordionContent, AccordionItem, AccordionTrigger } from "@/components/ui/accordion";

export default function Specialists() {
  const [name, setName] = useState<string>("");
  const [category, setCategory] = useState<Category[]>([]);
  const [title, setTitle] = useState<string>("");
  const [author, setAuthor] = useState<string>("");
  const [content, setContent] = useState<string>("");
  const [post, setPosts] = useState<Posts[]>([]);
  const [idPost, setIdPost] = useState<number | null>(null);
  const [isCategoryValid, setIsCategoryValid] = useState(false);
  const [isPostValid, setIsPostValid] = useState(false); 

  useEffect(() => {
    const categoryValid = name !== "";
    setIsCategoryValid(categoryValid);
  }, [name]);

  useEffect(() => {
    const postValid = title !== "" && author !== "" && content !== "";
    setIsPostValid(postValid);
  }, [title, author, content]);

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

  useEffect(() => {
    console.log(idPost);
  }, [idPost]);

  function deleteCategory(){

  }

  const columns = createColumns(handleDelete);

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
        </div>
          {category.map((category) => (
            <Drawer key={category.id}>
              <DeleteWrapper message="Mensaje" key={1} del={() => deleteCategory()}>
              <Badge key={category.id} variant="outline" className="cursor-pointer">{category.name}</Badge>
              </DeleteWrapper>
            </Drawer>
          ))}
       
        <br></br>
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

        <div style={{ display: 'flex', alignItems: 'center' }}>
          <div style={{ flex: 1 }}>
            
            <DataTable data={post} columns={columns} setId={setIdPost}/>
          </div>
          
        </div>


      </div>
    </div>
  );
}
