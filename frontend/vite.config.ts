import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      "/api": {
        target: "http://192.168.0.41:8080",
        changeOrigin: true,
      },
      "/translator": {
        target: "http://192.168.0.41:8080",
        changeOrigin: true,
      },
    },
  },
});
