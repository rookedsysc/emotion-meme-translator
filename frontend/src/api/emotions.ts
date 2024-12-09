import { Emotion } from "../types/emotion";

export const getImageUrl = (imagePath: string): string => {
  if (!imagePath) {
    console.error("Image path is undefined or empty");
    return ""; // 또는 기본값을 반환
  }

  if (imagePath.startsWith("http")) {
    return imagePath;
  }

  // /images/sample1.jpeg -> /api/images/sample1.jpeg
  const imageFileName = imagePath.split("/").pop();
  return `/api/images/${imageFileName}`;
};

export const fetchEmotions = async (): Promise<Emotion[]> => {
  try {
    const response = await fetch("/meme/emotions");
    if (!response.ok) {
      throw new Error("Failed to fetch emotions");
    }
    return response.json();
  } catch (error) {
    console.error("Error fetching emotions:", error);
    return [];
  }
};
