// src/utils/__tests__/posts.test.ts

import { test, expect, vi } from 'vitest';
import postAPI from '../api/post'; // Ajusta la ruta según tu estructura de carpetas
import { get, del, post } from '@/utils/methods';

vi.mock('@/utils/methods', () => ({
  get: vi.fn(),
  del: vi.fn(),
  post: vi.fn(),
}));

test('should create a post', async () => {
  const mockPost = {
    id: 1,
    title: 'Test Post',
    author: 'Author Name',
    date: '2024-10-11',
    content: 'This is a test post',
  };

  (post as jest.Mock).mockResolvedValueOnce(mockPost);

  const result = await postAPI.posts.setPost(mockPost);

  expect(result).toEqual(mockPost);
  expect(post).toHaveBeenCalledWith("/posts", mockPost);
});

test('should fetch all posts', async () => {
  const mockPosts = [
    { id: 1, title: 'Post 1', author: 'Author 1', date: '2024-01-01', content: 'Content 1' },
    { id: 2, title: 'Post 2', author: 'Author 2', date: '2024-01-02', content: 'Content 2' },
  ];

  (get as jest.Mock).mockResolvedValueOnce(mockPosts);

  const result = await postAPI.posts.getPosts();

  expect(result).toEqual(mockPosts);
  expect(get).toHaveBeenCalledWith("/posts");
});

test('should delete a post', async () => {
  (del as jest.Mock).mockResolvedValueOnce(undefined);

  const postId = 1;
  const result = await postAPI.posts.deletePost(postId);

  expect(result).toBeUndefined();
  expect(del).toHaveBeenCalledWith(`/posts/${postId}`);
});

test('should update a post', async () => {
  const mockPost = {
    id: 1,
    title: 'Updated Post',
    author: 'Author Name',
    date: '2024-10-11',
    content: 'This is an updated post',
  };

  (post as jest.Mock).mockResolvedValueOnce(undefined);

  const result = await postAPI.posts.updatePost(mockPost.id, mockPost);

  expect(result).toBeUndefined();
  expect(post).toHaveBeenCalledWith(`/posts/${mockPost.id}`, mockPost);
});
