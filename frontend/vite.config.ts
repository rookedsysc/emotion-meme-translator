import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

const ip = "localhost";
const apiUrl = `http://${ip}:8080`;

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      "/api": {
        target: apiUrl,
        changeOrigin: true,
      },
      "/translator": {
        target: apiUrl,
        changeOrigin: true,
      },
    },
  },
});
