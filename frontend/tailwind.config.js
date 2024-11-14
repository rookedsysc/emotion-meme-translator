/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        jua: ["Jua", "sans-serif"],
        noto: ["Noto Sans KR", "sans-serif"],
        nanum: ["Nanum Pen Script", "cursive"],
      }
    },
  },
  plugins: [],
}

