// IOpenApiCocoroboService.aidl
package jp.co.sharp.openapi.cocorobo;

// Declare any non-default types here with import statements

interface IOpenApiCocoroboService {
  String control(String apiKey, String mode);
  String getData(String apiKey, String dataKind);
}