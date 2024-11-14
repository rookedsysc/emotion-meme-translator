import { Emotion } from "../types/emotion";

export const getImageUrl = (imagePath: string): string => {
  if (imagePath.startsWith("http")) {
    return imagePath;
  }
  return imagePath; // proxy를 사용하므로 전체 URL이 필요 없음
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
