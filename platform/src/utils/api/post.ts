import {get, del, post } from "@/utils/methods";

export interface Posts {
    id: number;
    title: string;
    author: string;
    date: string;
    content: string
}


   


export default {
    posts: {
        setPost: (
            posts: Posts
        ): Promise<Posts> => {
            return post("/posts", posts)
        },

        getPosts: (): Promise<Posts[]> =>{
            return get("/posts")
        },

        deletePost: (id: number): Promise<void> => {
            return del(`/posts/${id}`)
        },

        updatePost: (
            id: number,
            posts: Posts) : Promise<void> => {
            return post(`/posts/${id}`, posts)
        }



    }
}