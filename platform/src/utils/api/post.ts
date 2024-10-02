import {get, del, post, upload } from "@/utils/methods";

export interface Posts {
    title: string;
    author: string;
    date: string;
    content: string
}


export default {
    posts: {
        setPost: (
            posts: Posts
        ): Promise<void> => {
            return post("/posts", posts)
        },

        getPosts: (): Promise<void> =>{
            return get("/posts")
        },

        deletePost: (id: number): Promise<void> => {
            return del(`/posts/${id}`)
        }

    }
}