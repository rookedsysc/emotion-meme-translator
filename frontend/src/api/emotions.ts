import { Emotion } from "../types/emotion";

export const getImageUrl = (imagePath: string): string => {
  if (imagePath.startsWith("http")) {
    return imagePath;
  }

  // /images/sample1.jpeg -> /api/images/sample1.jpeg
  const imageFileName = imagePath.split("/").pop();
  return `/api/images/${imageFileName}`;
};

export const fetchEmotions = async (): Promise<Emotion[]> => {
  try {
    const response = await fetch("/translator/emotions");
    if (!response.ok) {
      throw new Error("Failed to fetch emotions");
    }
    return response.json();
  } catch (error) {
    console.error("Error fetching emotions:", error);
    return [];
  }
};
