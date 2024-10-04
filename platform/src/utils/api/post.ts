import {get, del, post, upload } from "@/utils/methods";

export interface Posts {
    title: string;
    author: string;
    date: string;
    content: string
}

export interface PostID{
    id: number;
}


export default {
    posts: {
        setPost: (
            posts: Posts
        ): Promise<PostID> => {
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