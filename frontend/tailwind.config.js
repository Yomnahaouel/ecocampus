/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,jsx}'],
  theme: {
    extend: {
      colors: {
        eco: {
          50: '#ecfdf5',
          100: '#d1fae5',
          200: '#a7f3d0',
          500: '#10B981',
          600: '#059669',
          700: '#047857',
          900: '#064e3b',
        },
      },
    },
  },
  plugins: [],
}
